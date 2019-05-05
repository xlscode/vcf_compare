package org.lscode.vcf_compare;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiffCard {
    private WrappedVCard vCard;
    private List<String> numberList = new ArrayList<>();

    public DiffCard(){}

    public DiffCard(WrappedVCard card){
        vCard = card;
    }

    public DiffCard(WrappedVCard card, List<String> numbers){
        vCard = card;
        numberList.addAll(numbers);
    }

    public String description(){
        String nl = System.getProperty("line.separator");
        return numberList.stream().map(s->"  > " + s).collect(Collectors.joining(nl, vCard.name()+nl, "")) + nl;
    }
}
