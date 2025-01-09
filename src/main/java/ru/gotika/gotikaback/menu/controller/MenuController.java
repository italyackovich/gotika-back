package ru.gotika.gotikaback.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gotika.gotikaback.menu.dto.MenuDto;
import ru.gotika.gotikaback.menu.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuDto>> getAllMenus() {
        return ResponseEntity.ok(menuService.getAllMenu());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MenuDto> createMenu(@RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<MenuDto> updateMenu(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return ResponseEntity.ok(menuService.updateMenu(id, menuDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenuById(id);
        return ResponseEntity.noContent().build();
    }


}
