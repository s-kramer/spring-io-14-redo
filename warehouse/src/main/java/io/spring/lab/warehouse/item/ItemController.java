package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/item",
        consumes= MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class ItemController {
    private static final String ITEM_ID_PATH_VARIABLE = "itemId";
    private ItemService itemService;

    @GetMapping
    public List<ItemRepresentation> getAllItems() {
        return itemService.findAll().stream()
                .map(ItemRepresentation::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{itemId}")
    public ItemRepresentation getItem(@PathVariable(ITEM_ID_PATH_VARIABLE) long itemId) {
        return ItemRepresentation.of(itemService.findOne(itemId));
    }

    @PutMapping
    public ItemRepresentation updateItem(ItemUpdate itemUpdate) {
        return ItemRepresentation.of(itemService.update(itemUpdate));
    }

    @PostMapping
    public ItemRepresentation createItem(@RequestBody  ItemRepresentation itemRepresentation) {
        final Item item =
                itemService.create(new Item(null, itemRepresentation.getName(), itemRepresentation.getCount(), itemRepresentation.getPrice()));
        return ItemRepresentation.of(item);
    }

}
