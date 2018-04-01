package com.udeafx.data.service;

import com.crmfx.guice.scanner.serviceProvider.ServiceProvider;
import com.crmfx.util.constant.MongoReferences;
import com.crmfx.util.constant.PageReferences;
import com.crmfx.util.result.AsyncHandlerResult;
import com.crmfx.util.result.JsonResult;
import com.google.inject.Inject;
import com.udeafx.service.facade.dataAccesser.DataAccessService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ProxyHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * created by:xmf
 * date:2018/3/14
 * description:
 */
@Slf4j
@ServiceProvider
public class MongoAccesser implements DataAccessService {

    @Inject
    private MongoClient mongoClient;
    @Inject
    private Vertx vertx;

    public void register() {
        ProxyHelper.registerService(DataAccessService.class, vertx.getDelegate(), this, EB_ADDRESS);
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
     * @param params
     * @param handler
     * @return
     */
    public DataAccessService save(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxSave(col(params), MongoReferences.DOCUMENT.json(params))
                .subscribe(id -> AsyncHandlerResult.succ(id, handler));
        return this;
    }

    /**
     * find document list from collection.
     *
     * @param params
     * @param handler
     * @return
     */
    public DataAccessService find(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        JsonObject findOptions = MongoReferences.FIND_OPTIONS.json(params);
        FindOptions options = findOptions.size() > 0 ? new FindOptions(findOptions) : new FindOptions();
        mongoClient.rxFindWithOptions(col(params), MongoReferences.QUERY.json(params), options)
                .map(JsonArray::new)
                .subscribe(rows -> AsyncHandlerResult.succWithRows(rows, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * find single document from collection.
     *
     * @param params
     * @param handler
     * @return
     */
    public DataAccessService findOne(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxFindOne(col(params), MongoReferences.QUERY.json(params), MongoReferences.FIELDS.json(params))
                .subscribe(doc -> AsyncHandlerResult.succ(doc, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * remove document from collection.
     *
     * @param params
     * @param handler
     * @return
     */
    public DataAccessService remove(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxRemove(col(params), MongoReferences.QUERY.json(params))
                .subscribe(v -> AsyncHandlerResult.succ(handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * return document count.
     *
     * @param params
     * @param handler
     * @return
     */
    public DataAccessService count(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.rxCount(col(params), MongoReferences.QUERY.json(params))
                .subscribe(count -> AsyncHandlerResult.succ(count, handler),
                        err -> AsyncHandlerResult.fail(err, handler));
        return this;
    }

    /**
     * execute origin db statement
     *
     * @param params
     * @param handler
     * @return
     */
    public DataAccessService execute(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
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
    private FindOptions page2findOptions(JsonObject pageOptions) {
        String sortFlag = PageReferences.SORT_BY.val();
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
