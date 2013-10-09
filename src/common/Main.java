/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;

/**
 *
 * @author Robby
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Hub asdf = new Hub(5000);
        Client client1 = new Client("127.0.0.1", 5000) {

            @Override
            protected void messageReceived(Object message) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    
}
