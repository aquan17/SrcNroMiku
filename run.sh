#!/bin/bash
# Khoi chay NRO Yuki Game Server
java -server -Xms1G -Xmx3G -XX:+UseG1GC -Dfile.encoding=UTF-8 -jar dist/Nro_Miku.jar
