/**
 *  file:    WrappedVCards.java
 *  desc:    Non-checked exception needed to use checked exception throwing methods in stream lambdas
 *  author:  ls-code
 *  license: GNU General Public License v3.0
 */

package org.lscode.vcf_compare;

public class RTIOException extends RuntimeException {

    public RTIOException(String message){
        super();
    }

    public RTIOException(Throwable cause){
        super();
    }
}
