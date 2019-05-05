/**
 *  file:    VcfCmp.java
 *  desc:    the main class of the vcf_compare package
 *  author:  ls-code
 *  license: GNU General Public License v3.0
 */

package org.lscode.vcf_compare;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class VcfCmp {
    public static void main(String[] args ){
        WrappedVCards newVCs = null;
        WrappedVCards oldVCs = null;

        if (args.length != 3){
            System.err.println("three parameters needed");
            System.exit(1);
        }

        String prefix = args[0];
        Path newVCPath = Paths.get(args[1]);
        Path oldVCPath = Paths.get(args[2]);

        try {
            newVCs = new WrappedVCards(newVCPath, prefix);
        }
        catch (RTIOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (newVCs.size() == 0){
            System.err.println("The \"new\" vcard set is empty. Nothing to do!");
            System.exit(1);
        }

        try {
            oldVCs = new WrappedVCards(oldVCPath, prefix);
        }
        catch (RTIOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (oldVCs.size() == 0){
            System.err.println("The \"old\" vcard set is empty. Nothing to do!");
            System.exit(1);
        }


        List<DiffCard> result =  newVCs.notContained(oldVCs);

        result.stream().map(d->d.description()).forEach(System.out::println);
    }
}
