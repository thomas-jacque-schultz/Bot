# -*- encoding: utf-8 -*-

import os
import discord
from discord.ext import commands, tasks
from docker_manager import get_all_servers_status

# Initialisation du bot
intents = discord.Intents.default()
bot = commands.Bot(command_prefix="!", intents=intents)

# Variables globales
STATUS_CHANNEL_ID = 1234567890  # Remplacez par l'ID du canal où le statut sera affiché
status_message = None  # Message à mettre à jour

@bot.event
async def on_ready():
    print(f"{bot.user.name} est connecté !")
    update_status.start()  # Démarrer la tâche périodique
    await initialize_status_message()

async def initialize_status_message():
    """Créer ou récupérer le message de statut."""
    global status_message
    channel = bot.get_channel(STATUS_CHANNEL_ID)
    if not channel:
        print("Canal introuvable. Vérifiez l'ID.")
        return

    async for message in channel.history(limit=10):  # Cherche dans l'historique du canal
        if message.author == bot.user:
            status_message = message
            break

    if not status_message:
        status_message = await channel.send("Initialisation du statut des serveurs...")

@tasks.loop(minutes=1)
async def update_status():
    """Mettre à jour le statut des serveurs toutes les minutes."""
    global status_message
    if not status_message:
        return

    statuses = get_all_servers_status()
    embed = discord.Embed(title="Statut des serveurs", color=0x00ff00)
    for server, status in statuses.items():
        embed.add_field(name=server, value=f"**État :** {status}", inline=False)

    await status_message.edit(content=None, embed=embed)

# Commande manuelle pour rafraîchir le statut
@bot.command()
async def status(ctx):
    statuses = get_all_servers_status()
    embed = discord.Embed(title="Statut des serveurs", color=0x00ff00)
    for server, status in statuses.items():
        embed.add_field(name=server, value=f"**État :** {status}", inline=False)
    await ctx.send(embed=embed)


# Charge le token depuis les variables d'environnement
TOKEN = os.getenv("DISCORD_TOKEN")

if not TOKEN:
    raise ValueError("Le token Discord n'est pas défini. Vérifiez votre fichier .env ou votre configuration.")

bot.run(TOKEN)
