#!/bin/sh
cd ../bin
JAVA="../jre/bin/java"
if [ ! -f "$JAVA" ]; then
    JAVA="java"
fi

"$JAVA" -Xms256m -Xmx768m -XX:MaxPermSize=256m -cp ".:../lib/vpplatform.jar:../lib/bpva-help.jar:../lib/jniwrap.jar:../lib/winpack.jar:../ormlib/orm.jar:../ormlib/orm-core.jar:../lib/xalan.jar:../lib/lib01.jar:../lib/lib02.jar:../lib/lib03.jar:../lib/lib04.jar:../lib/lib05.jar:../lib/lib06.jar:../lib/lib07.jar:../lib/lib08.jar:../lib/lib09.jar:../lib/lib10.jar" RV "$@"