package src;

import java.io.*;
import java.util.*;

public class BOMBA_29 {

    // ==============================
    // MATRIZ DEL AUTÓMATA
    // ==============================
    static int[][] dpMatriz = {
      // a   b   c   d   t    
        {1, -1, -1, -1, -1}, // estado 0 (q0)
        {-1, 2, -1, -1, -1}, // estado 1 (q1)
        {-1, 2, -1, -1, -1}  // estado 2 (q2)
    };
    // Se agrego a la matriz el arsenal c, d y t.

    static Map<Character, Integer> dpMapa = new HashMap<>();

    static {
        dpMapa.put('a', 0);
        dpMapa.put('b', 1);
        dpMapa.put('c', 2);
        dpMapa.put('d', 3);
        dpMapa.put('t', 4);
    }

    // ==============================
    // VALIDAR CADENA 
    // ==============================
    public static boolean dpValidar(String dpCadena) {
        int dpEstado = 0;

        for (char dpChar : dpCadena.toCharArray()) {
            Integer dpCol = dpMapa.get(dpChar);
            if (dpCol == null) return false;

            dpEstado = dpMatriz[dpEstado][dpCol];
            if (dpEstado == -1) return false;
        }

        return dpEstado == 2;
    }

    // ==============================
    // LOADING ANIMADO
    // ==============================
    public static void dpLoading() {
        String[] dpAnim = {"\\", "|", "/", "-"};

        for (int i = 0; i <= 100; i += 25) {
            System.out.print("\rLoading " + dpAnim[i % 4] + " " + i + "%");
            try { Thread.sleep(80); } catch (Exception e) {}
        }
    }

    // ==============================
    // LOGIN
    // ==============================
    public static boolean dpLogin(Scanner dpSc) {

        String[][] dpUsuarios = {
            {"Pesantez", "1234"},
            {"pat_mic", "1234"}
        };

        int dpIntentos = 3;

        while (dpIntentos-- > 0) {

            System.out.print("Usuario: ");
            String dpUser = dpSc.nextLine();

            System.out.print("Contraseña: ");
            String dpPass = dpSc.nextLine();

            boolean dpValido = Arrays.stream(dpUsuarios)
                    .anyMatch(u -> u[0].equals(dpUser) && u[1].equals(dpPass));

            if (dpValido) return true;

            System.out.println("Eres un intruso...");
        }

        return false;
    }

    // ==============================
    // MAIN
    // ==============================
    public static void main(String[] args) {

        Scanner dpSc = new Scanner(System.in);

        if (!dpLogin(dpSc)) {
            System.out.println("Sistema bloqueado.");
            return;
        }

        System.out.println("\nACCESO:");
        System.out.println("Nombre: David Pesántez");
        System.out.println("Cédula: 17-28871029");

        List<String> dpExplosiones = new ArrayList<>();

        System.out.println("\n[+] COORDENADAS UCRANIANAS:\n");

        // ENCABEZADO TABLA 
        System.out.printf("%-10s %-12s %-10s %-10s %-12s %-10s %-10s %-15s\n",
                "Loading", "Geoposición", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Tipo Arsenal");

        try {

            InputStream dpIs = BOMBA_29.class.getResourceAsStream("/PesantezDavid.csv");

            if (dpIs == null) {
                System.out.println("Archivo no encontrado en /src");
                return;
            }

            BufferedReader dpBr = new BufferedReader(new InputStreamReader(dpIs));

            String dpLinea;
            dpBr.readLine(); // saltar cabecera

            while ((dpLinea = dpBr.readLine()) != null) {

                String[] dpDatos = dpLinea.split(";");

                String dpCoord = dpDatos[0].trim();
                String dpLunes = dpDatos[1].trim();
                String dpMartes = dpDatos[2].trim();
                String dpMiercoles = dpDatos[3].trim();
                String dpJueves = dpDatos[4].trim();
                String dpViernes = dpDatos[5].trim();
                String dpArsenal = dpDatos[6].trim();

                dpLoading();

                // IMPRIME FILA TABLA
                System.out.printf("\r%-10s %-12s %-10s %-10s %-12s %-10s %-10s %-15s\n",
                        "100%",
                        dpCoord,
                        dpLunes,
                        dpMartes,
                        dpMiercoles,
                        dpJueves,
                        dpViernes,
                        dpArsenal
                );

                boolean dpValido = dpValidar(dpArsenal);

                boolean dpExplota =
                        dpValido &&
                        (dpCoord.equals("Coord-02") || dpCoord.equals("Coord-09"));

                if (dpExplota) {
                    dpExplosiones.add(dpCoord + "|" + dpArsenal);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ==============================
        // RESULTADO FINAL 
        // ==============================
        System.out.println("\n...");
        System.out.println("[+] BOMB-29: COORDENADAS UCRANIANAS A DESTRUIR:\n");

        System.out.printf("%-12s %-15s\n", "Geoposición", "Tipo Arsenal");

        dpExplosiones.forEach(dp -> {
            String[] partes = dp.split("\\|");
            System.out.printf("%-12s %-15s\n",
                    partes[0].trim(),
                    partes[1].trim());
        });
    }
}
