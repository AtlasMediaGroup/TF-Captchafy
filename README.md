# Captchafy - The only spambot solution you'll ever need.

### What is Captchafy?
Captchafy is a Spigot plugin/extension for Minecraft servers. Its sole purpose is to protect against spambot attacks. When a server is under attack, Captchafy directs players to solve a captcha before joining. This prevents spambots from flooding and crashing a server. Attacks are automatically detected by Captchafy, so you can kick back and let Captchafy do the work.

### How does Captchafy work?
From a user's standpoint, during an attack, they will be kicked and ask to solve a captcha at example.com:port/captcha/. Once the captcha is solved, their ip is added to a list of authorized IPs and they are allowed to join.

Captchafy runs on a lightweight, embedded Grizzly container for Jersey. This allows us to make full use of the jax-rs API. When a player accesses the captcha URL in their browser, they send an HTTP/GET request to the server, which is processed by Captchafy. Captchafy serves the user the captcha. The player solves the captcha. Their captcha data is POSTed back to the server, and is then sent off to Google to be verified. Once Google gives the okay, the player's IP is added to the list of authorized IPs.

### Features
- Captcha-based Spam-bot Prevention.
- IP Whitelisting
- Automatic Attack Detection
- Multiple Security Modes
- Check the Issues tab for more.

### How do I use Captchafy?
Captchafy comes with one simple command: /captchafy.

Usage: /captchafy <on:off> <friendly:moderate:strict> or /captchafy <on:off> <1:2:3>
The on/off parameter will turn on/turn off Captcha based verification. The second parameter is the security level.

Security levels:
1/Friendly - Players will only have to solve a captcha once. Their IPs will be saved in a config for future logins.
2/Moderate - Players will have to solve one captcha every time the server reloads. (Recommended)
3/Strict - Players will have to solve a captcha every time they join.

Captchafy will auto-enable/disable when the server is attacked. You can force Captchafy to stay off by using the command /captchafy off -f. Captchafy will not auto-disable if you enable it using the command.

You will need to assign the permission 'captchafy.command' to anyone who needs to use /captchafy. It is assigned to OPs by default.

You can also whitelist IPs in the config. These IPs will never have to solve a captcha.

### How do I install Captchafy?
Installation is fairly straight-forward. Drag Captchafy to your plugins folder like any other add-on, and start your server. This will allow Captchafy to generate the necessary config files. Then, edit the config as you please. You will need to obtain a reCaptcha key from Google. You can do that here: https://www.google.com/recaptcha/. Place the keys in the config file, and you're ready to go! Be sure to open a port up in your firewall, as well as port forward if you are creating a server on a home connection.

---

### Compiling
Captchafy makes use of Maven, so it should compile automatically. If you are using Intellij or Eclipse, you might need to run 'mvn package' in the command line to build the project. Netbeans does this for you. You shouldn't need to add any dependencies or make edits to the POM. However, you MUST use the jar labeled Captchafy-jar-with-dependencies. If you don't, you will get NoClassDefFound errors. If you have any problems, open an issue.

### License
Captchafy is licensed under the GNU General Public License v3.0. You can find a copy of it in the License.txt file.
