package org.mendora.data.service;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ProxyHelper;
import lombok.extern.slf4j.Slf4j;
import org.mendora.guice.scanner.serviceProvider.ServiceProvider;
import org.mendora.service.facade.dataAccesser.MongoAccesser;
import org.mendora.util.constant.MongoReferences;
import org.mendora.util.constant.PageReferences;
import org.mendora.util.result.AsyncHandlerResult;
import org.mendora.util.result.JsonResult;
import rx.Single;

/**
 * created by:xmf
 * date:2018/3/14
 * description:
 */
@Slf4j
@ServiceProvider
public class MongoAccesserImpl implements MongoAccesser {

    @Inject
    private MongoClient mongoClient;
    @Inject
    private Vertx vertx;

    public void register() {
        ProxyHelper.registerService(MongoAccesser.class, vertx.getDelegate(), this, EB_ADDRESS);
    }

    /**
     * unzip collection parameter.
     *
     * @param params
     * @return
     */
    private String col(JsonObject params) {
        return MongoReferences.COLLECTION.str(params);
    }

    /**
     * insert/update document from collection.
     *
     * @param params{ "collection":<collection name>,
     *                "document":<insert document>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser save(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxSave(col(params), MongoReferences.DOCUMENT.json(params))
                .subscribe(id -> AsyncHandlerResult.succ(id, handler));
        return this;
    }

    /**
     * find document list from collection.
     *
     * @param params{ "collection":<collection name>,
     *                "query":<query parameter>,
     *                "findOptions":<find options>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser find(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        JsonObject findOptions = MongoReferences.FIND_OPTIONS.json(params);
        FindOptions options = findOptions.size() > 0 ? new FindOptions(findOptions) : new FindOptions();
        mongoClient.rxFindWithOptions(col(params), MongoReferences.QUERY.json(params), options)
                .map(JsonArray::new)
                .subscribe(rows -> AsyncHandlerResult.succWithRows(rows, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * find document list from collection.
     *
     * @param params{ "collection":<collection name>,
     *                "query":<query parameter>,
     *                "page":<page parameter>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser findWithPage(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        JsonObject page = MongoReferences.PAGE.json(params);
        FindOptions options = pageOptions2findOptions(page);
        JsonObject query = MongoReferences.QUERY.json(params);
        mongoClient.rxFindWithOptions(col(params), query, options)
                .map(JsonArray::new)
                .flatMap(rows ->
                        rxCount(params).map(totalSize -> {
                            page.put(PageReferences.TOTAL_SIZE.val(), totalSize);
                            JsonObject rs = JsonResult.allocate(3)
                                    .put(MongoReferences.PAGE.val(), page)
                                    .put(PageReferences.ROWS.val(), rows);
                            // 'query' field not empty?
                            return query.size() > 0 ? rs.put(MongoReferences.QUERY.val(), query) : rs;
                        })
                )
                .subscribe(doc -> AsyncHandlerResult.succ(doc, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * find single document from collection.
     *
     * @param params{ "collection":<collection name>,
     *                "query":<query parameter>,
     *                "fields":<fields mapper set>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser findOne(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxFindOne(col(params), MongoReferences.QUERY.json(params), MongoReferences.FIELDS.json(params))
                .subscribe(doc -> AsyncHandlerResult.succ(doc, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * remove document from collection.
     *
     * @param params{ "collection":<collection name>,
     *                "query":<query parameter>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser remove(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxRemove(col(params), MongoReferences.QUERY.json(params))
                .subscribe(v -> AsyncHandlerResult.succ(handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * counting document.
     *
     * @param params{ "collection":<collection name>,
     *                "query":<query parameter>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser count(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        rxCount(params).subscribe(count -> AsyncHandlerResult.succ(count, handler),
                err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * counting document with rx format.
     *
     * @param params{ "collection":<collection name>,
     *                "query":<query parameter>
     *                }
     * @return
     */
    private Single<Long> rxCount(JsonObject params) {
        return mongoClient.rxCount(col(params), MongoReferences.QUERY.json(params));
    }

    /**
     * execute origin db statement
     *
     * @param params{ "commandName":<command name>,
     *                "command":<command statement>
     *                }
     * @param handler
     * @return
     */
    public MongoAccesser execute(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxRunCommand(MongoReferences.COMMAND_NAME.str(params), MongoReferences.COMMAND.json(params))
                .subscribe(result -> AsyncHandlerResult.succ(result, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * page options convert to find options.
     *
     * @param pageOptions {
     *                    size:<current page size>,
     *                    currPage:<current page number>,
     *                    sortBy:<sort by json object>,
     *                    fields:<mapping fields from document>
     *                    }
     * @return FindOptions Mongo format find options.
     */
    private FindOptions pageOptions2findOptions(JsonObject pageOptions) {
        boolean hasSizeField = pageOptions.containsKey(PageReferences.SIZE.val());
        boolean hasCurrPageField = pageOptions.containsKey(PageReferences.CURR_PAGE.val());
        if (!(hasSizeField && hasCurrPageField))
            return new FindOptions();
        String sortFlag = PageReferences.SORT_BY.val();
        // has 'sortBy' field?
        if (!pageOptions.containsKey(sortFlag))
            // default sort by "_id" field asc.
            pageOptions.put(sortFlag, JsonResult.one().put(MongoReferences._ID.val(), MongoReferences.ASC.number()));
        FindOptions findOptions = new FindOptions()
                .setSkip(PageReferences.SIZE.number(pageOptions) * (PageReferences.CURR_PAGE.number(pageOptions) - 1))
                .setLimit(PageReferences.SIZE.number(pageOptions))
                .setSort(PageReferences.SORT_BY.json(pageOptions));
        // has 'fields' field ?
        return pageOptions.containsKey(MongoReferences.FIELDS.val()) ?
                findOptions.setFields(MongoReferences.FIELDS.json(pageOptions)) : findOptions;
    }

}
