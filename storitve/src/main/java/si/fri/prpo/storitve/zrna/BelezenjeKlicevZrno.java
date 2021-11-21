package si.fri.prpo.storitve.zrna;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {

    private List<String> classes = new ArrayList<>();
    private List<String> methods = new ArrayList<>();
    private List<Integer> calls = new ArrayList<>();

    private static Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());

    public void beleziSteviloKlicev(String className, String methodName) {

        int ixOfClass = classes.indexOf(className);
        //int ixOfMethod = methods.indexOf(methodName);

        if(ixOfClass != -1) {

            String methodNameAtIx = methods.get(ixOfClass);

            if(methodNameAtIx == methodName) {

                int val = calls.get(ixOfClass);
                val++;
                calls.set(ixOfClass, val);

                log.info("Stevilo klicev metode " + methodName + " iz razreda: " + className + ": " + val);

            }

        } else {

            classes.add(className);
            methods.add(methodName);
            calls.add(1);

            log.info("Stevilo klicev metode " + methodName + " iz razreda: " + className + ": " + 1);
        }

    }
}
