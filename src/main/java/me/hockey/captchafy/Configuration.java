/*
    Copyright (C) 2017 James Depp

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package me.hockey.captchafy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Configuration
{
    //TODO Add time based authorization expiration.
    FileConfiguration ips;
    File ipsFile = new File(Captchafy.plugin.getDataFolder(), "ips.yml");
    FileConfiguration config;
    File configFile = new File(Captchafy.plugin.getDataFolder(), "config.yml");
    public List<String> ipList = new ArrayList();
    public List<String> alwaysAuthorizedList = new ArrayList();

    public void startup() throws IOException
    {
        saveConfig();
        saveNames(false);
        loadConfigs();
    }

    public boolean isIncomplete()
    {
        return getHostname() == null || getPort() == null || getCaptchaSiteKey() == null || getCaptchaSecret() == null || config.get("always-on") == null || config.get("security-level") == null;
    }

    //TODO Cleanup. This actually generates the config, but saves the ips if true. Causes NPE if saved before loadConfig()
    public void saveNames(boolean save) throws IOException
    {
        if (ipsFile == null)
        {
            ipsFile = new File(Captchafy.plugin.getDataFolder(), "ips.yml");
        }
        if (!ipsFile.exists())
        {
            Captchafy.plugin.saveResource("ips.yml", false);
        }
        if (save)
        {
            ips.set("authorized-ips", alwaysAuthorizedList);
            ips.save(ipsFile);
        }
    }

    //TODO Cleanup. Just generates config, not saves it.
    public void saveConfig() throws IOException
    {
        if (configFile == null)
        {
            configFile = new File(Captchafy.plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists())
        {
            Captchafy.plugin.saveResource("config.yml", false);
        }
    }

    public void loadConfigs() throws IOException
    {
        config = YamlConfiguration.loadConfiguration(configFile);
        ips = YamlConfiguration.loadConfiguration(ipsFile);
        alwaysAuthorizedList = ips.getStringList("authorized-ips");
    }

    public void setAuthorized(String ip, boolean authorize)
    {
        if (!authorize)
        {
            ipList.remove(ip);
            return;
            //We dont remove always authorized ips, because they were added while in the friendly mode, so they should still be added to the ips.yml.
        }
        switch (Captchafy.securityLevel)
        {
            case 1:
            {
                if (!ipList.contains(ip)) ipList.add(ip);
                alwaysAuthorizedList.add(ip);
            }
            case 2:
            {
                if (!ipList.contains(ip)) ipList.add(ip);
            }
            case 3:
            {
                if (!ipList.contains(ip)) ipList.add(ip);
            }
        }
    }

    public boolean isAuthorized(String ip)
    {
        if (config.getStringList("whitelisted-ips").contains(ip))
        {
            return true;
        }
        switch (Captchafy.securityLevel)
        {
            case 1:
            {
                return alwaysAuthorizedList.contains(ip) || config.getStringList("authorized-ips").contains(ip);
            }
            case 2:
            {
                return ipList.contains(ip);
            }
            case 3:
            {
                return ipList.contains(ip);
            }
        }
        return false;
    }

    public String getHostname()
    {
        return config.getString("hostname");
    }

    public String getBindingIP()
    {
        return config.getString("binding-ip");
    }

    public String getPort()
    {
        return config.getString("port");
    }

    public String getCaptchaSiteKey()
    {
        return config.getString("recaptcha-key");
    }

    public String getCaptchaSecret()
    {
        return config.getString("recaptcha-secret");
    }
}