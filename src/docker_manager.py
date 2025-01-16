import docker

def get_server_status(container_name):
    client = docker.from_env()
    try:
        container = client.containers.get(container_name)
        status = container.status  # "running", "exited", etc.
        return status
    except docker.errors.NotFound:
        return "not found"
    except Exception as e:
        return f"error: {e}"

def get_all_servers_status():
    # Exemple avec plusieurs serveurs
    servers = {
        "minecraft_server": "Minecraft",
        "csgo_server": "CS:GO",
    }

    statuses = {}
    for container_name, server_name in servers.items():
        statuses[server_name] = get_server_status(container_name)
    return statuses
