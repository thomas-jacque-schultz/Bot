# -*- encoding: utf-8 -*-

import os
import discord
from discord.ext import commands, tasks
import ServeurState

# Initialisation du bot
intents = discord.Intents.default()
intents.guilds = True 
intents.messages = True
intents.message_content = True
bot = commands.Bot(command_prefix="!", intents=intents)

# Variables globales
STATUS_CHANNEL_NAME="technique"
STATUS_CHANNEL_ID = 1071740424831647837  # Remplacez par l'ID du canal
SERVER_TEMPLATE_DIR=".\\"
status_message = None  # Message updated
status_messages = []
technical_channels= dict()

@bot.event
async def on_ready():
    print(f"{bot.user.name} est connecté !")
    update_status.start()  # Demarrer le cron
    await init_state()
#    await initialize_status_message()

async def init_state():
    guilds = bot.guilds
    for guild in guilds :
        channels = guild.channels
        for channel in channels:
            if(channel.name.__contains__("technique") | channel.name.__contains__("bot")):
                technical_channels[channel] = await get_servers_state_list() 
    

    for technical_channel, serveur_states in technical_channels.items() :
        async for message in channel.history(limit=10):
             print(message.content)

async def get_servers_state_list():
    states = list()

    for fichier in os.listdir(SERVER_TEMPLATE_DIR):
        chemin_fichier = os.path.join(SERVER_TEMPLATE_DIR, fichier)
        if os.path.isfile(chemin_fichier):
            with open(chemin_fichier, 'r', encoding='utf-8') as file:
                contenu = file.read()
                print(f'Contenu du fichier {fichier} :')
                print(contenu)
                print('-' * 40)
        
    return states

#async def initialize_status_message():
#    """Créer ou récupérer le message de statut."""
#    global status_message
#    channel = bot.get_channel(STATUS_CHANNEL_ID)
#    if not channel:
#        print("Canal introuvable. Vérifiez l'ID.")
#        return
#
#    async for message in channel.history(limit=10):  # Cherche dans l'historique du canal
#        if message.author == bot.user:
#            status_message = message
#            break
#
#    if not status_message:
#        status_message = await channel.send("Initialisation du statut des serveurs...")

@tasks.loop(minutes=1)
async def update_status():
    """Mettre à jour le statut des serveurs toutes les minutes."""
    
#    global status_message
#    if not status_message:
#        return

#    statuses = get_all_servers_status()
#    embed = discord.Embed(title="Statut des serveurs", color=0x00ff00)
#    for server, status in statuses.items():
#        embed.add_field(name=server, value=f"**état :** {status}", inline=False)
#    
#    await status_message.edit(content=None, embed=embed)

# Commande manuelle pour rafraichir le statut
#@bot.command()
#async def status(ctx):
#    statuses = get_all_servers_status()
#    embed = discord.Embed(title="Statut des serveurs", color=0x00ff00)
#    for server, status in statuses.items():
#        embed.add_field(name=server, value=f"**état :** {status}", inline=False)
#    await ctx.send(embed=embed)


# Charge le token depuis les variables d'environnement
TOKEN = os.getenv("DISCORD_TOKEN")

if not TOKEN:
    raise ValueError("Le token Discord n'est pas défini. Vérifiez votre fichier .env ou votre configuration.")

bot.run(TOKEN)
