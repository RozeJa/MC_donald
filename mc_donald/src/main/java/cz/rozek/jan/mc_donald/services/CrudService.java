package cz.rozek.jan.mc_donald.services;

import java.util.List;
import java.util.NoSuchElementException;

public interface CrudService<E,ID> {
    /**
     * Složí pro vytvoření nového záznamu v db
     * @param data instance třídy, která se má uložit
     * @return instance třídy, už uložená v db
     * @throws ValidationException pokud instance není validní dojde k vydození vyjímky
     * @throws DuplicateKeyException pokud instance obsahuje duplicitní hodnotu, která nemůže být doplicitní
     */
    E create(E data) throws ValidationException, DuplicateKeyException;

    /**
     * Slouží pro získání instance z db pomocí id
     * @param id id záznamu
     * @return instance třídy načtená z db
     * @throws NoSuchElementException poduk záznam neexistuje je vrácena vyjímka
     */
    E read(ID id)  throws NoSuchElementException;

    /**
     * Najde všechny záznamy třídy E a vrátí je
     * @return List instancí 
     */
    List<E> readAll();

    /**
     * Proveda update instance
     * @param id id záznamu, který je aktualizován
     * @param data aktualizovaný záznam
     * @return aktuální instance 
     * @throws ValidationException pokud instance není validní dojde k vydození vyjímky
     * @throws DuplicateKeyException pokud instance obsahuje duplicitní hodnotu, která nemůže být doplicitní
     */
    E update(ID id, E data) throws ValidationException, DuplicateKeyException;

    /**
     * Metoda odebere rárnam z db
     * @param id id záznamu, který má být odebrán
     * @return odebraný záznam
     * @throws NoSuchElementException pokud odebíraný zaznam neexistuje, dojde k vyvolání vyjímky
     */
    E delete(ID id) throws NoSuchElementException;

    /**
     * Metoda zkontroluje validitu záznamu, pokud je vše v pořádku nic jiného neudělá.
     * Pokud nastane problém vyhodí vylímky ValidationException
     * @param data validovaný záznam
     * @throws ValidationException pokud záznam není validní je vyhoztena tato vyjímka
     * @throws DuplicateKeyException pokud instance obsahuje duplicitní hodnotu, která nemůže být doplicitní
     */
    void validate(E data) throws ValidationException, DuplicateKeyException;
}
