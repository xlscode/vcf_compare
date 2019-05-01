# VCard Sets Compare
Analyzes two sets of VCards* and Outputs the phone numbers included 
in the first set but not in the other.

*) https://en.wikipedia.org/wiki/VCard

Each of the sets may be:
* a vcf file containing many contacts
* a directory containing many vcf files with one contact in each 
(as exported by Nokia Symbian phones, for example) 

The compared elements are phone numbers, so if both sets contain 
a numbers but in differently named contacts, the number is not reported.

#### Usage:

    > java -jar vcf_compare.jar prefix path_to_first_set path_to_second_set

        prefix - local phone country code

        path_to_first_set - may be path to a directory or to a vcf file 
        
        path_to_second_set -                 --//--

The local country prefix is needed to recognize the same numbers written with or without the country code

#### Used libraries:
* ez-vcard by Mike Angstadt (mangstadt) - really, great lib, saved me a lot of work\
github repo: https://github.com/mangstadt/ez-vcard \
maven groupId: com.googlecode.ez-vcard

* junit for testing (not much of this, so far)

#### License
GNU General Public License v3.0
