#!/bin/bash
# ----------------------------------------------------------------------
# name:         单实例管理脚本
# version:      1.4
# author:       Jieven
# url:        	https://eova.cn
# ----------------------------------------------------------------------

# 自定义配置

# ----------------------------------------------------------------------
# 实例端口号
APP_PORT=9090
# 项目名
APP_MOD=eova-meta
# 项目启动类
MAIN_CLASS=cn.eova.meta.RunEovaMeta

# 实例名
APP_NAME=${APP_MOD}-${APP_PORT}
# JVM参数 -Djava.awt.headless=true
APP_OPTS="-Xms256m -Xmx4g -Dundertow.port=${APP_PORT} -Dapp.name=${APP_NAME}"
# ----------------------------------------------------------------------
# 项目打包文件名
RELEASE_NAME=${APP_MOD}-release.zip

#自定义Java环境变量(EOVA基于JAVA8,如默认为JAVA8,可注释下2行)
export JAVA_HOME=/usr/java/java8
export PATH=$JAVA_HOME/bin:$PATH
# ----------------------------------------------------------------------


COMMAND="$1"
C2="$2"


# 生成 class path 值
APP_BASE_PATH=$(cd `dirname $0`; pwd)
CP=${APP_BASE_PATH}/config:${APP_BASE_PATH}/lib/*

# 后台运行 日志输出到指定文件
function run() {
    # 运行为后台进程，并且将信息输出到 console.log(多实例输出到同一个文件,方便集中查看)
    nohup java -Xverify:none ${APP_OPTS} -cp ${CP} ${MAIN_CLASS} >> console.log 2>&1 &
}

# 运行并查看日志
function start(){
  run
  tail -50f console.log
}

function stop(){
    # kill 命令不使用 -9 参数时，会回调 onStop() 方法，一般建议kill -2
    # 根据实例名停止服务
    kill $(pgrep -f ${APP_NAME}) 2>/dev/null

    # 短暂休眠0.1秒
    sleep 0.1

	  echo "-- stop succeed"
}

function status(){
  PID=$(pgrep -f ${APP_NAME})
    if [[ "$PID" == "" ]]; then
		echo "-- Undertow [${APP_NAME}] stopped !!!"
		exit 0
	fi
    #memery(KB)
  M1=$(cat /proc/$PID/status | grep -e VmRSS | awk 'NR==1 {print $2}')
    #memery%
  M2=$(top -b -n1 | grep $PID | head -1 | awk '{print $10}')
    #cpu%
  CPU=$(top -b -n1 | grep $PID | head -1 | awk '{print $9}')
  PORT=${APP_PORT}
    #get tcp
  TCP=$(netstat -nat | grep $PORT | grep -E "ESTABLISHED" | wc -l)
  TCP2=$(netstat -nat | grep $PORT | grep -E "TIME_WAIT" | wc -l)
    #get thread
  THREAD=$(ps -Lf $PID | wc -l)
  echo -n "-- Undertow [${APP_NAME}] running >> pid=$PID memery=$((M1 / 1024))M/$M2% cpu=$CPU% port=$PORT tcp=$TCP/$TCP2 thread=$THREAD"
    echo
}
if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "run" ]]; then
  run
elif [[ "$COMMAND" == "stop" ]]; then
    stop
elif [[ "$COMMAND" == "release" ]]; then
	echo "------------------------------"
	echo "-- Release Start..."
	echo "------------------------------"

	stop

  # 精简打包模式不删lib包, 减少上传时间
  if [[ "$C2" != "mini" ]]; then
		rm -rf ./lib/
		echo "-- clean lib succeed"
	fi
	
	rm -rf ./config/ ./webapp/
	echo "-- clean config,webapp succeed"
	
	unzip -o -q ${RELEASE_NAME}
  echo "-- unzip -q $RELEASE_NAME succeed"

	if [ ! -d "history" ]; then
        mkdir history
	fi
	mv ${RELEASE_NAME} ./history/last-release-$(date "+%Y%m%d%H%M%S").zip
	echo "-- Backup the last version succeed"
	
	echo "------------------------------"
  echo "-- Release succeed"
	echo "------------------------------"
	
  if [[ "$C2" == "log" ]]; then
      echo "-- Project start..."
    start
  else
    run
      echo "-- Project run..."
  fi

elif [[ "$COMMAND" == "restart" ]]; then
    stop

    start
elif [[ "$COMMAND" == "status" ]]; then
    status
else
  echo "Usage: $0 | run | start | stop | restart | release"

	exit 0
fi
