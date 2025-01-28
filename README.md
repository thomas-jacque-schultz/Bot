# Bot


    // Channel -> Server
    // -> Event id - Message Id
    // Mise à jour des message sur le server.
    // (Command) event.getJda().getServerById()
    // ServerMapper.map(...)
    // Server.refreshMessage()


In discord user ID format is `245281978054737920`

# Command format

    /[command] [option1] [option2] [option3]
    [  
        payloadLine1 : value1
        payloadLine2 : value2
        payloadLine3 : value3
    ]

# Adding a Gaming Server Watcher

payload :

    identifier : [gaming-assetto]
    name : [Assetto Corsa]
    url : [assetto.schultz-thomas.fr]
    playersMax : [12]
    installation : [Download Modpack From Drive]
    version : [1.16]
    description : [Course à paramétré sur l'interface Web]
    admins : [Chacha | Pisel]
