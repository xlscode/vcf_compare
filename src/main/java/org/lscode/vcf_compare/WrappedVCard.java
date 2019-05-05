/**
 *  file:    WrappedVCard.java
 *  desc:    Class to wrap an ez-vcard VCard object
 *  author:  ls-code
 *  license: GNU General Public License v3.0
 */

package org.lscode.vcf_compare;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Organization;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WrappedVCard {
    private VCard source;
    private String prefix;


    public WrappedVCard(VCard aVCard, String aPrefix){
        source = aVCard;
        prefix = aPrefix;
    }

    public WrappedVCard(String vcString, String prefix) {
        this.source = Ezvcard.parse(vcString).first();
        this.prefix = prefix;
        List<String> phoneNumbers = collectNumbers();
    }

    public boolean containsPhoneNumber(String number){
        List<String> phoneNumbers = collectNumbers();
        return phoneNumbers.contains(stripNumber(number));
    }

    public List<String> getPhoneNumbers(){
        return collectNumbers();
    }

    public List<String> getRawPhoneNumbers(){
        return collectRawNumbers();
    }

    public VCard source(){
        return this.source;
    }

    public String name(){
        String given = "";
        String family = "";
        StructuredName strName = source.getStructuredName();
        if (strName != null) {
            given = strName.getGiven();
            given = (given == null) ? "" : given;
            family = strName.getFamily();
            family = (family == null) ? "" : family;
        }
        List<String> orgValues;
        String orgString = "";
        Organization org = source.getOrganization();
        if (org != null){
            orgValues = org.getValues();
            orgString = orgValues.stream().collect(Collectors.joining(" "));
        }
        return (given + "  " + family + " " + orgString).trim();
    }

    @Override
    public String toString() {
        return source.toString();
    }

    private List<String> collectNumbers(){
        List<Telephone> VCNumbers = source.getTelephoneNumbers();

        return VCNumbers.stream().map(n->stripNumber(n.getText())).collect(Collectors.toList());
    }

    private List<String> collectRawNumbers(){
        List<Telephone> VCNumbers = source.getTelephoneNumbers();

        return VCNumbers.stream().map(n->n.getText()).collect(Collectors.toList());
    }

//    public List<String> commonNumbersWith(WrappedVCard otherVC){
//
//    }

    public Optional<DiffCard> numbersNotContained(WrappedVCards wrappedVCards){
        List<String> numbers = collectRawNumbers().stream().filter(n->!wrappedVCards.containsPhoneNumber(n)).collect(Collectors.toList());
        if (numbers.size() > 0 ){
            return Optional.of(new DiffCard(this, numbers));
        }
        else{
            return Optional.empty();
        }
    }

    private String stripNumber(String number){
        int startPos = 0;
        int endPos = number.length();

        if (number.charAt(0) == '+'){
            String subs = number.substring(1,3);
            if (subs.equals(prefix)){
                startPos = 3;
            }
        }

        return number.subSequence(startPos, endPos).chars()
                .mapToObj(i->Character.valueOf((char)i))
                .filter(c->c>='0' && c<='9')
                .map(c->Character.toString(c))
                .collect(Collectors.joining());
    }
}
