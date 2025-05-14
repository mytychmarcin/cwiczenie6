/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/

//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

class WrongStudentName extends Exception {
}

class WrongAge extends Exception {
}

class WrongDateOfBirth extends Exception {
}

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                int ex = menu();
                switch (ex) {
                    case 1:
                        exercise1();
                        break;
                    case 2:
                        exercise2();
                        break;
                    case 3:
                        exercise3();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("blad: wybor poza zakresem wybierz cyfre od 0 do 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("blad: wpisz cyfre");
                scan.nextLine();
            } catch (IOException e) {
                System.out.println("blad wejscia/wyjscia.");
            } catch (WrongStudentName e) {
                System.out.println("bledne imie studenta");
            } catch (WrongAge e) {
                System.out.println("bledny wiek studenta 1-99");
            } catch (WrongDateOfBirth e) {
                System.out.println("bledna data urodzenia. poprawny format to dd-mm-yyyy.");
            }
        }
    }

    public static int menu() {
        System.out.println("\nWcisnij:");
        System.out.println("1 - aby dodac studenta");
        System.out.println("2 - aby wypisac wszystkich studentow");
        System.out.println("3 - aby wyszukac studenta po imieniu");
        System.out.println("0 - aby wyjsc z programu");
        int choice = scan.nextInt();

        if (choice < 0 || choice > 3) {
            System.out.println("blad. wybierz cyfre od 0 do 3");
            return -1;
        }
        return choice;
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.print("Podaj imie: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        var name = ReadName();

        System.out.print("Podaj wiek: ");
        var age = scan.nextInt();
        if (age < 1 || age > 99)
            throw new WrongAge();

        scan.nextLine();
        System.out.print("Podaj date urodzenia (dd-mm-yyyy): ");
        var date = scan.nextLine();

        if (!date.matches("\\d{2}-\\d{2}-\\d{4}"))
            throw new WrongDateOfBirth();

        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.print("Podaj imie: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono:");
            System.out.println(wanted.ToString());
        }
    }
}
