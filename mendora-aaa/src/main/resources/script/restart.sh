#!/bin/sh
#java 环境变量
source /etc/profile
export JAVA_HOME=/home/pi/jdk1.8
export JRE_HOME=$JAVA_HOME/jre

#判断传入的参数数量是不是够
if [ $# != 2 ] ; then 
echo "USAGE: $0 SERVICE_DIR SERVICE_NAME_PRE" 
echo " e.g.: $0 /opt/ync-goods ync-goods" 
exit 1; 
fi 
SERVICE_DIR=$1
SERVICE_NAME_PRE=$2

echo "SERVICE_DIR" $SERVICE_DIR
echo "SERVICE_NAME_PRE" $SERVICE_NAME_PRE

cd $SERVICE_DIR

 P_ID=`jps -ml|grep "$SERVICE_NAME_PRE"|awk '{print $1}'` 
        if [ "$P_ID" == "" ]; then
            echo "=== $SERVICE_NAME process not exists or stop success"
        else
            echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
       fi

    sleep 3

#找出找出当前 jar 名称
    JAR_NAME=`ls -rt *.jar|grep -v 'sources'|tail -1`

    echo "START JAR_NAME "$JAR_NAME
    $JRE_HOME/bin/java -Xms256m -Xmx512m -jar $JAR_NAME > $SERVICE_NAME_PRE.log  2>&1 &
exit