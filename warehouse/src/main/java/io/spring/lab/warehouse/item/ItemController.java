package io.spring.lab.warehouse.item;

import static org.springframework.http.HttpStatus.CREATED;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/item",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class ItemController {
    private static final String ITEM_ID = "itemId";
    private static final String ITEM_ID_PATH_VARIABLE = "/{" + ITEM_ID + "}";
    private ItemService itemService;

    @GetMapping
    public List<ItemRepresentation> getAllItems() {
        return itemService.findAll().stream()
                .map(ItemRepresentation::of)
                .collect(Collectors.toList());
    }

    @GetMapping(ITEM_ID_PATH_VARIABLE)
    public ItemRepresentation getItem(@PathVariable(ITEM_ID) long itemId) {
        return ItemRepresentation.of(itemService.findOne(itemId));
    }

    @PutMapping(ITEM_ID_PATH_VARIABLE)
    public ItemRepresentation updateItem(@PathVariable(ITEM_ID) long itemId, @RequestBody ItemUpdate itemUpdate) {
        return ItemRepresentation.of(itemService.update(itemUpdate.withId(itemId)));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ItemRepresentation createItem(@RequestBody ItemRepresentation itemRepresentation) {
        return ItemRepresentation.of(itemService.create(itemRepresentation.asItem()));
    }

}
