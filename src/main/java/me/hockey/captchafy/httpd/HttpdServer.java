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
package me.hockey.captchafy.httpd;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import me.hockey.captchafy.Captchafy;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class HttpdServer
{
    public static final String BASE_URI = "http://" + (Captchafy.configs.getBindingIP().isEmpty() ? Captchafy.configs.getHostname() : Captchafy.configs.getBindingIP()) + ":" + Captchafy.configs.getPort() + "/";
    public static HttpServer getServer()
    {
        final ResourceConfig rc = new ResourceConfig().packages("me.hockey.captchafy.httpd");
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(CaptchaPage.class);
        rc.registerClasses(classes);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}
