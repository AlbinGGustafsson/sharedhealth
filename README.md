<div align="center">

# SharedHealth

A Fabric Minecraft mod that syncs your health and hunger across the entire server. 

![SharedHealth Logo](https://github.com/Neddslayer/sharedhealth/blob/master/src/main/resources/assets/sharedhealth/textures/icon.png)

---

## Usage

- **Shared Health**: Toggle with `/gamerule shareHealth true/false`
- **Shared Hunger**: Toggle with `/gamerule shareHunger true/false`

---

## Fork Features (abbish-remix)

This fork adds the `/setsharedhealth <amount>` command for hardcore multiplayer servers.

### `/setsharedhealth <amount>`

Sets the shared health pool for all players. Perfect for hardcore servers where you want to start with custom HP.

**What it does:**
- Sets `limitHealth` gamerule to `false`
- Sets max health for all online players
- Heals everyone to full HP
- Syncs the shared health component
- **Can only be run once per world** (this is made for my auto looping hc server)

**Example:**
```
/setsharedhealth 100
```
Gives all players 100 HP (50 hearts) that stays synced across the server.

---

</div>
