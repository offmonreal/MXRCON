Manual create plugin

For copy after build plugin chage build.xml

add to <target> 

<target name = "-post-jar"> 
<copy todir = "../../../Terminal/MXRCON/plugins/"> 
<fileset dir = "${dist.dir}" /> 
</copy> 


#Имя файла в META-INF/services/ должно быть как имя сервиса в полном пути каждого отдельно (с именем интерфеса)
А внутри на каждой строке список классов... которые реалезуют этот сервис.. обычно один
