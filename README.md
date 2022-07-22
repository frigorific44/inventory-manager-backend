# inventory-manager-backend
The back-end API and database connection for an inventory management web app.

## API Reference

### /im/company/

#### `POST`—/im/company/*companyId*

##### Request Body:

```json
{
    "name": "Company Name"
}
```

#### `GET`—/im/company/*companyId*

##### Response Body:

```json
{
	"id": 0,
	"name": "Company Name"
}
```



#### `UPDATE`—/im/company/*companyId*

##### Request Body:

```json
{
	"id": 0,
	"name": "Updated Company Name"
}
```



#### `DELETE`—/im/company/*companyId*



### /im/warehouse/

#### `POST`—/im/warehouse/*warehouseId*

##### Request Body:

```json
{
	"name": "Warehouse Name",
	"description": "A warehouse description.",
	"parentId": 0
}
```

#### `GET`—/im/warehouse/*companyId*

##### Response Body:

```json
{[
	...
	{
		"id": 0,
		"name": "Warehouse Name",
		"description": "A warehouse description.",
		"parentId": 0
	},
	...
]}
```



#### `UPDATE`—/im/warehouse/*warehouseId*

##### Request Body:

```json
{
	"id": 0,
	"name": "Updated Warehouse Name",
	"description": "An updated warehouse description.",
	"parentId": 0
}
```



#### `DELETE`—/im/warehouse/*warehouseId*



### /im/section/

#### `POST`—/im/section/*sectionId*

##### Request Body:

```json
{
	"name": "Section Name",
	"description": "A section description.",
    "capacity": 0,
	"parentId": 0
}
```

#### `GET`—/im/section/*warehouseId*

##### Response Body:

```json
{[
	...
	{
		"id": 0,
		"name": "Section Name",
		"description": "A section description.",
		"capacity": 0,
		"parentId": 0
	},
	...
]}
```



#### `UPDATE`—/im/section/*sectionId*

##### Request Body:

```json
{
	"id": 0,
	"name": "Updated Section Name",
	"description": "An updated section description.",
	"capacity": 0,
	"parentId": 0
}
```



#### `DELETE`—/im/section/*sectionId*

### /im/item/

#### `POST`—/im/item/*sectionId*/*itemId*

##### Request Body:

```json
{
	"id": 0,
    "name": "Item Name",
    "alt": "ALT-ID",
	"description": "A item description.",
    "count": 0,
	"parentId": 0
}
```

#### `GET`—/im/section/*sectionId*

##### Response Body:

```json
{[
	...
	{
		"id": 0,
		"name": "Item Name",
		"alt": "ALT-ID",
		"description": "A item description.",
		"count": 0,
		"parentId": 0
	},
	...
]}
```



#### `UPDATE`—/im/section/*sectionId*/*itemId*

##### Request Body:

```json
{
	"id": 0,
	"name": "Item Name",
	"alt": "ALT-ID",
	"description": "A item description.",
	"count": 0,
	"parentId": 0
}
```



#### `DELETE`—/im/section/*sectionId*
