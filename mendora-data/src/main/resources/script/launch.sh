#!/bin/bash

#java env
#shell脚本有时候调用linux的环境变量会有问题，所以这里还是把用到的java环境再设置一下
export JAVA_HOME=/opt/software/jdk1.8

#app name 为了确保脚本通用
APP_NAME=mendora-data

SERVICE_DIR=/opt/project/$APP_NAME
SERVICE_NAME=mendora-data-1.0-SNAPSHOT-prod
APP_JAR=$SERVICE_NAME\.jar
#启动app的时候，将进程的pid保存在这里，方便以后杀死进程用
APP_PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR

case "$1" in

    start)
        #/dev/null 就不会有nohup.out文件了。
        nohup $JAVA_HOME/bin/java -Xms32m -Xmx128m -jar $APP_JAR >/dev/null 2>&1 &
        #将pid写入文件
        echo $! > $SERVICE_DIR/$APP_PID
        echo "===== start $SERVICE_NAME"
        ;;

    stop)
        kill `cat $APP_PID`
        #删除掉pid文件
        rm -f $SERVICE_DIR/$APP_PID
        #查看是不是杀死进程了
        sleep 5
                #获得进程号，复制号两边不能留空格
                pid=`ps -ef | grep -w $SERVICE_NAME | grep -v grep | awk '{print $2}'`
        #判断相等两边要留空格
        if ["$pid" == ""];then
            echo "=== kill successfully "
        else
            echo "===== kill fail and try to kill -9"
            echo "====  the pid is  $pid"
            kill -9 $pid
        fi
        echo "stop finished"
        ;;
    *)
        echo "please use start or stop 参数"
        ;;
esac
exit 0
