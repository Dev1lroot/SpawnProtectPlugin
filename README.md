# 🧱 SpawnProtect

A lightweight Paper plugin that protects a defined area from **explosions** (TNT, creepers, withers, etc.).  
You can set the center and radius of the protected zone in the config file.

---

## ✨ Features
- Prevents block destruction caused by **explosions** inside a configurable radius  
- Fully compatible with **Paper 1.21.7+**  
- Works with TNT, creepers, ghast fireballs, withers, and more  
- Simple and lightweight — no unnecessary overhead  

---

## ⚙️ Configuration (`config.yml`)

```yaml
# Center of the protected area (world coordinates)
center-x: 0
center-z: 0

# Radius of the protected zone in blocks
# Blocks inside this radius cannot be destroyed by explosions.
protected-radius: 1000
