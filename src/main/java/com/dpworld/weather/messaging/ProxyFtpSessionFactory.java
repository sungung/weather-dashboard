package com.dpworld.weather.messaging;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.NestedIOException;
import org.springframework.integration.ftp.session.AbstractFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketException;

/**
 * Taken from
 * http://forum.springsource.org/showthread.php?120012-Restarting-a-previously-stopped-FTP-inbound-channel-adapter
 *
 * @author gunnar@springsource forum
 *
 */
public class ProxyFtpSessionFactory extends AbstractFtpSessionFactory<FTPClient> {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyFtpSessionFactory.class);

    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    @Override
    public FtpSession getSession() {
        try {
            return new FtpSession(this.createClient());
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to create FTPClient", e);
        }
    }

    private FTPClient createClient() throws SocketException, IOException {
        final FTPClient client = this.createClientInstance();

        // if we don't have this we get  Host attempting data connection xx.xx.xx.xx is not same as server xx.xx.xx.xx
        client.setRemoteVerificationEnabled(false);

        Assert.notNull(client, "client must not be null");
        client.configure(this.config);
        Assert.hasText(this.username, "username is required");

        this.postProcessClientBeforeConnect(client);

        // Connect
        boolean usingProxy = false;
        if (proxyHost != null && proxyHost.length() > 0) {
            // Connect using proxy with authentication
            Assert.isTrue(proxyPort > 0, "proxyPort number should be > 0");
            usingProxy = true;
            // connect
            try {
                client.connect(((InetSocketAddress) new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress(proxyHost, proxyPort)).address()).getAddress());
            } catch (IOException ioe) {
                throw new NestedIOException("Connecting to proxy [" +
                        proxyHost + ":" + proxyPort + "] failed. Please check the connection.", ioe);
            }
            // check reply
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                throw new MessagingException("Connecting to proxy [" +
                        proxyHost + ":" + proxyPort + "] failed. Please check the connection.");
            }
            LOG.debug("Connected to proxy [{}:{}]", proxyHost, proxyPort);
        } else {
            // Connect using no proxy
            try {
                client.connect(this.host, this.port);
            } catch (IOException ioe) {
                throw new NestedIOException("Connecting to server [" +
                        host + ":" + port + "] failed. Please check the connection.", ioe);
            }
            // check reply
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                throw new MessagingException("Connecting to server [" +
                        host + ":" + port + "] failed. Please check the connection.");
            }
            LOG.debug("Connected to server [{}:{}]", host, port);
        }

        if (usingProxy && proxyUsername != null) {
            // login using proxy w/ auth
            LOG.debug("Login attempt to server [" + username + "@" +host + ":" + port + "] using proxy w/ authentication");
            try {
                if (!client.login(username + "@" + host + " " + proxyUsername, password, proxyPassword)) {
                    throw new IllegalStateException("Login to server [" + username + "@" + host + ":" + port + "] using proxy [" +
                            proxyUsername + "@" + proxyHost + ":" + proxyPort + "] failed. The response from the server is: " +
                            client.getReplyString());
                }
            } catch (IOException ioe) {
                throw new NestedIOException("Login to server [" + username + "@" + host + ":" + port + "] using proxy [" +
                        proxyUsername + "@" + proxyHost + ":" + proxyPort + "] failed.", ioe);
            }
        }
        else if (usingProxy) {
            // login using proxy
            LOG.debug("Login attempt to server [" + username + "@" +host + ":" + port + "] using proxy");
            try {
                if (!client.login(username + "@" + host, password)) {
                    throw new IllegalStateException("Login to server [" + username + "@" + host + ":" + port +
                            "] failed. The response from the server is: " +
                            client.getReplyString());
                }
            } catch (IOException ioe) {
                throw new NestedIOException("Login to server [" + username + "@" + host + ":" + port +
                        "] failed.", ioe);
            }
        } else {
            // direct login
            LOG.debug("Login attempt to server [" + username + "@" +host + ":" + port + "]");
            try {
                if (!client.login(username, password)) {
                    throw new IllegalStateException("Login to server [" + username + "@" + host + ":" + port +
                            "] failed. The response from the server is: " +
                            client.getReplyString());
                }
            } catch (IOException ioe) {
                throw new NestedIOException("Login to server [" + username + "@" + host + ":" + port +
                        "] failed.", ioe);
            }
        }
        LOG.debug("Connected to server [{}:{}]: {}", new Object[]{host, port, client.getReplyString()});

        this.postProcessClientAfterConnect(client);

        this.updateClientMode(client);
        client.setFileType(fileType);
        client.setBufferSize(bufferSize);
        return client;
    }

    private void updateClientMode(FTPClient client) {
        switch (this.clientMode) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE:
                client.enterLocalActiveMode();
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE:
                client.enterLocalPassiveMode();
                break;
            default:
                break;
        }
    }

    @Override
    protected FTPClient createClientInstance() {
        return new FTPClient();
    }

    @Required
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    @Required
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

}