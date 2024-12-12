package ru.gotika.gotikaback.menu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.menu.mapper.MenuMapper;
import ru.gotika.gotikaback.menu.model.Menu;
import ru.gotika.gotikaback.menu.repository.MenuRepository;
import ru.gotika.gotikaback.menu.service.MenuService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public List<MenuDto> getAllMenu() {
        return menuMapper.fromMenuListToMenuDtoList(menuRepository.findAll());
    }

    @Override
    public MenuDto getMenuById(Long id) {
        return menuMapper.menuToMenuDto(menuRepository.findById(id).orElse(null));
    }

    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        Menu menu = menuRepository.save(menuMapper.menuDtoToMenu(menuDto));
        return menuMapper.menuToMenuDto(menu);
    }

    @Override
    public MenuDto updateMenu(Long id, MenuDto menuDto) {
        return menuRepository.findById(id).map(menu -> {
            menuRepository.save(menuMapper.menuDtoToMenu(menuDto));
            return menuDto;
        }).orElse(null);
    }

    @Override
    public void deleteMenuById(Long id) {
        menuRepository.deleteById(id);
    }
}
