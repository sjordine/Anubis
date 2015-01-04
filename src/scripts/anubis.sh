#!/bin/bash


#Reference variables - Change to fit your environment
ANUBIS_FOLDER="$HOME"
ANUBIS_LIBS_FOLDER="$HOME/Anubis_lib/"
ANUBIS_MAIN_JAR="Anubis.jar"
ANUBIS_MAIN_CLASS="br.unicamp.ic.anubis.Anubis"
ANUBIS_PLUGIN_FOLDER="$HOME/plugins"

export ANUBIS_PLUGIN_PATH="$ANUBIS_PLUGIN_FOLDER"

ANUBIS_LIBS=`ls ${ANUBIS_LIBS_FOLDER}/*.jar`

#Loop to mount classpath associated with reference libraries
#It creates a list separated by colons. It also starts with a colon
#as Anubis main library will be added before it on classpath parameter
LIBS_CLASSPATH=""
for lib in $ANUBIS_LIBS
do
 LIBS_CLASSPATH="${LIBS_CLASSPATH}:${lib}"
done

#Anubis execution

echo $ANUBIS_PLUGIN_PATH


echo "Executing Anubis..."
java -cp "${ANUBIS_FOLDER}/${ANUBIS_MAIN_JAR}${LIBS_CLASSPATH}" ${ANUBIS_MAIN_CLASS}