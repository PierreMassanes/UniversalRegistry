# UniversalRegistry

_de Chloé Gulielmi & Pierre Massanès_

## Détails de lancement du registre

D'abord il faut lancer un RMI registry au niveau du out/production/UniversalRegistry avec la commande 
    
    rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 1099

Pour lancer le registre universel, il suffit d'exécuter le main de la classe Server 
et mettre les arguments suivants dans la JVM :

    -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase="http://Axxx:1098/" 
    -Djava.security.policy=/home/user/IdeaProjects/UniversalRegistry/src/server/policy_server

