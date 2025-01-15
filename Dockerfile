# Étape 1 : Utiliser une image Python légère
FROM python:3.9-slim

# Étape 2 : Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3 : Copier les fichiers du projet dans le conteneur
COPY src/ ./src/

# Étape 4 : Installer les dépendances Python
RUN pip install --no-cache-dir -r requirements.txt

# Étape 5 : Définir la commande pour lancer le bot
CMD ["python", "src/bot.py"]
