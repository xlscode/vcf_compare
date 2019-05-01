/**
 *  file:    WrappedVCards.java
 *  desc:    Collection of WrappedVCard objects
 *  author:  ls-code
 *  license: GNU General Public License v3.0
 */

package org.lscode.vcf_compare;

import ezvcard.Ezvcard;
import ezvcard.VCard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WrappedVCards {
    private List<WrappedVCard> vcardList = new ArrayList<>();
    private String prefix = "";

    public WrappedVCards(String aPrefix) {
        prefix = aPrefix;
    }

    public WrappedVCards(Path vcPath, String aPrefix) throws RTIOException{
        prefix = aPrefix;
        File vcFile = vcPath.toFile();

        if (vcFile.isFile()) {
            parseFromFile(vcFile);
        }
        else if (vcFile.isDirectory()){
            parseFromFolder(vcPath);
        }
    }

    public Stream<WrappedVCard> stream(){
        return vcardList.stream();
    }

    public List<WrappedVCard> all(){
        return vcardList;
    }

    public void add(WrappedVCard aWrappedVCard){
        vcardList.add(aWrappedVCard);
    }

    public WrappedVCard get(int index){
        return vcardList.get(index);
    }

    public int size(){
        return vcardList.size();
    }

    public boolean containsPhoneNumber(String number){
        return vcardList.stream().anyMatch(v->v.containsPhoneNumber(number));
    }

    public Map<WrappedVCard, List<String>> notContained(WrappedVCards otherCards){
        Map<WrappedVCard, List<String>> result = new HashMap<>();

        for (WrappedVCard card : vcardList){
            List<String> numbers = card.numbersNotContained(otherCards);
            if (!numbers.isEmpty()){
                result.put(card, numbers);
            }
        }
        return result;
    }

    private void parseFromFile(File vcFile) throws RTIOException{
        List<VCard> ezVCs = null;
        try {
            ezVCs = Ezvcard.parse(vcFile).all();
        }
        catch (IOException e){
            throw new RTIOException(e);
        }
        vcardList = ezVCs.stream().map(v->new WrappedVCard(v, prefix)).collect(Collectors.toList());
        prefix = prefix;
    }

    private void parseFromFolder(Path vcfolder) throws RTIOException{

        try (Stream<Path> paths = Files.find(vcfolder, Integer.MAX_VALUE,
                (path,attrs) -> attrs.isRegularFile() && path.toString().endsWith(".vcf"))) {
            paths.forEach(p -> addVCFromFile(p.toFile()));
        }
        catch(IOException e){
            throw new RTIOException(e);
        }
    }

    private void addVCFromFile(File file) throws RTIOException{
        try {
            vcardList.add(new WrappedVCard(Ezvcard.parse(file).first(), prefix));
        }
        catch (IOException e){
            throw new RTIOException(e);
        }
    }

}
