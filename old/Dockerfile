# �tape 1 : Utiliser une image Python l�g�re
FROM python:3.9-slim

# �tape 2 : D�finir le r�pertoire de travail dans le conteneur
WORKDIR /app

# �tape 3 : Copier les fichiers du projet dans le conteneur
COPY src/ ./src/

# �tape 4 : Installer les d�pendances Python
RUN pip install --no-cache-dir -r ./src/requirements.txt

# �tape 5 : D�finir la commande pour lancer le bot
CMD ["python", "src/bot.py"]
