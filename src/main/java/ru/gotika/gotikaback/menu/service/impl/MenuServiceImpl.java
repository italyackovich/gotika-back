package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.menu.exception.MenuNotFoundException;
import ru.gotika.gotikaback.menu.mapper.MenuMapper;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.menu.repository.MenuRepository;
import ru.gotika.gotikaback.menu.service.MenuService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public List<MenuDto> getAllMenu() {
        log.info("Get all menu");
        return menuMapper.fromMenuListToMenuDtoList(menuRepository.findAll());
    }

    @Override
    public MenuDto getMenuById(Long id) {
        log.info("Get menu by id: {}", id);
        return menuMapper.menuToMenuDto(menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException("Menu with id " + id + " not found")));
    }

    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        Menu menu = menuRepository.save(menuMapper.menuDtoToMenu(menuDto));
        log.info("Create menu: {}", menuDto);
        return menuMapper.menuToMenuDto(menu);
    }

    @Override
    public MenuDto updateMenu(Long id, MenuDto menuDto) {
        return menuRepository.findById(id).map(menu -> {
            Menu updatedMenu = menuMapper.menuDtoToMenu(menuDto);
            updatedMenu.setId(menu.getId());
            menuRepository.save(updatedMenu);
            log.info("Update menu: {}", menu);
            return menuMapper.menuToMenuDto(updatedMenu);
        }).orElseThrow(() -> new MenuNotFoundException("Menu with id " + id + " not found"));
    }

    @Override
    public void deleteMenuById(Long id) {
        log.info("Delete menu by id: {}", id);
        menuRepository.deleteById(id);
    }
}
