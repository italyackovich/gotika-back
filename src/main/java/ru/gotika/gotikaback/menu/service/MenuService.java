package ru.gotika.gotikaback.menu.service;

import ru.gotika.gotikaback.menu.dto.MenuDto;

import java.util.List;

public interface MenuService {
    List<MenuDto> getAllMenu();
    MenuDto getMenuById(Long id);
    MenuDto createMenu(MenuDto menuDto);
    MenuDto updateMenu(Long id, MenuDto menuDto);
    void deleteMenuById(Long id);
}
