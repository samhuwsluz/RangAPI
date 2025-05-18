# RangAPI

**RangAPI** is a lightweight Bukkit plugin designed to manage and store player rank points in a MySQL database. It provides a simple API to get, set, and add points for players, and can be integrated easily into other Minecraft plugins.

## ✅ Features

- Store and retrieve player points via MySQL
- Simple API access to read and write points
- Configuration via `config.yml`
- Easy integration into existing Bukkit plugins

---

## 🛠 Installation

### 1. Plugin Setup

Download the plugin `.jar` and place it into your server's `plugins/` folder. On the next server start, the `config.yml` will be generated automatically.

### 2. Configuration

Edit the generated `config.yml`:

```yaml
database-url: "jdbc:mysql://your-host/database"
database-user: "your-username"
database-password: "your-password"
```

Make sure your MySQL server is accessible and that the database and user exist.

### 3. Database Schema

Your database must contain a table named `points` with the following structure:

```sql
CREATE TABLE `points` (
  `UUID` VARCHAR(36) NOT NULL,
  `points` INT(11) NOT NULL DEFAULT 0,
  `time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`UUID`)
);
```

---

## 📦 Maven Integration (for developers)

If you want to use RangAPI in your plugin, add it as a dependency via JitPack.

### 1. Add the JitPack repository

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```

### 2. Add the dependency

```xml
<dependency>
  <groupId>com.github.Samhuwsluz</groupId>
  <artifactId>RangAPI</artifactId>
  <version>alpha-5</version>
</dependency>
```

---

## 🔌 Bukkit Plugin Integration

In your plugin's `plugin.yml`, add:

```yaml
softdepend: [RangAPI]
```

---

## 📚 API Usage

### Getting access to the API

```java
RangAPI api = (RangAPI) Bukkit.getServer().getPluginManager().getPlugin("RangAPI");
```

Then access the point methods through:

```java
api.dbAPI.getPoints(player);
api.dbAPI.setPoints(player, int);
api.dbAPI.addPoints(player, int);
```

### Example:

```java
int currentPoints = api.dbAPI.getPoints(player);
api.dbAPI.addPoints(player, 10);
api.dbAPI.setPoints(player, 50);
```

---

## 🧱 Project Structure

- `RangAPI.java` – Main plugin class, handles database connection
- `DBAPI.java` – Provides a simple interface for point operations
- `PointsAPI.java` – Handles raw SQL operations
- `Database.java` – Manages MySQL connection

---

## ❗Important Notes

- **Do not hardcode your database credentials in the plugin.** Use the `config.yml` file.
- All SQL statements are currently built via string concatenation – be aware of SQL injection risks if you plan to expand the system.

---

## 📖 License

This plugin is distributed under the MIT License.

---

## 🤝 Contributions

Feel free to fork, improve, and submit pull requests! Feedback and suggestions are always welcome.