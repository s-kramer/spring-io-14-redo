package io.spring.lab.warehouse.item;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(ITEM_ID_PATH_VARIABLE + "/stock")
    public ItemRepresentation updateItemStock(@PathVariable(ITEM_ID) long itemId, @RequestBody ItemStockUpdate itemStockUpdate) {
        return ItemRepresentation.of(itemService.updateStock(itemStockUpdate.withId(itemId)));
    }

    @PostMapping
    public ResponseEntity<ItemRepresentation> createItem(@RequestBody ItemRepresentation itemRepresentation) {
        final ItemRepresentation createdItem = ItemRepresentation.of(itemService.create(itemRepresentation.asItem()));
        final URI link = linkTo(methodOn(ItemController.class).getItem(createdItem.getId())).toUri();

        return ResponseEntity.created(link).build();
    }

}
