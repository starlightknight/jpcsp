#!/bin/sh
java -Xmx2048m -Xss2m -XX:ReservedCodeCacheSize=64m -Djava.library.path=lib/linux-amd64 -jar bin/jpcsp.jar $@
