# BTK Innova Rent A Car Project With Spring Boot
This project cover all component needs in a Rent A Car company needs.
Car management ( adding, removing, updating cars)
Account Management (Individual and commercial users operations)
Rent Management (Car status management, user validation operations, billing
system )
#### http://localhost:8080/swagger-ui/index.html
## Technology
* Java
* Hibernate
* Spring core
* Spring MVC
* Spring Data
* Spring Rest
* Spring Validation
* Spring Security
## Database
* PostgreSql
## GitHub
**To clone the project;**
```git clone https://github.com/ErtanSidar/BTK-Innova-rentACar-backend-java.git```
### 1. Car Controller
```
PUT => /api/cars/update

POST => /api/cars/add

GET => /api/cars/getall

GET => /api/cars/findById/{id}

DELETE => /api/cars/delete/{id}
```
### 2. Brand Controller
```
PUT => /api/brands/update

POST => /api/brands/add

GET => /api/brands/getall

GET => /api/brands/findById/{id}

DELETE => /api/brands/delete/{id}
```
### 3. Color Controller
```
PUT => /api/colors/update

POST => /api/colors/add

GET => /api/colors/getall

GET => /api/colors/findById/{id}

DELETE => /api/colors/delete/{id}
```
### 4. City Controller
```
PUT => /api/cities/update

POST => /api/cities/add

GET => /api/cities/getall

DELETE => /api/cities/delete/{id}
```
### 5. Rental Controller
```
PUT => /api/rentals/update

POST => /api/rentals/addIndividual

POST => /api/rentals/addCorporate

GET => /api/rentals/getrentals

GET => /api/rentals/getall

DELETE => /api/rentals/delete/{id}
```
### 6. Promotion Controller
```
PUT => /api/promotions/update

POST => /api/promotions/add

GET => /api/promotions/getall

DELETE => /api/promotions/delete/{id}
```
### 7. Segment Controller
```
PUT => /api/segments/update

POST => /api/segments/add

GET => /api/segments/getall

DELETE => /api/segments/delete/{id}
```
### 8. Payment Controller
```
PUT => /api/payments/update

POST => /api/payments/add

GET => /api/payments/getall

DELETE => /api/payments/delete/{id}
```
### 9. Credit Card Info Controller
```
PUT => /api/credi-card-infos/update

POST => /api/credi-card-infos/add

GET => /api/credi-card-infos/getall

DELETE => /api/credi-card-infos/delete/{id}
```
### 10. Invoice Controller
```
POST => /api/invoices/add

GET => /api/invoices/getall

GET => /api/invoices/get-invoice-individual-customer/{rentalId}

GET => /api/invoices/get-invoice-corporate-customer/{rentalId}
```
### 11. Additional Service Controller
```
PUT => /api/additionalservices/update

POST => /api/additionalservices/add

GET => /api/additionalservices/getall

DELETE => /api/additionalservices/delete/{id}
```
### 12. Additional Rental Item Controller
```
PUT => /api/additionalRentalItems/update

POST => /api/additionalRentalItems/add

GET => /api/additionalRentalItems/getByRentalId

GET => /api/additionalRentalItems/getAll

DELETE => /api/additionalRentalItems/delete
```
### 13. Car Maintance Controller
```
PUT => /api/carMaintances/update

POST => /api/carMaintances/add

GET => /api/carMaintances/getall

DELETE => /api/carMaintances/delete/{id}
```
### 14. Car Damage Controller
```
PUT => /api/carDamages/update

POST => /api/carDamages/add

GET => /api/carDamages/getall

DELETE => /api/carDamages/delete/{id}
```
### 15. Individual Customer Controller
```
PUT => /api/individuals/update

POST => /api/individuals/add

GET => /api/individuals/getall

DELETE => /api/individuals/delete/{id}
```
### 15. Corporate Customer Controller
```
PUT => /api/corporates/update

POST => /api/corporates/add

GET => /api/corporates/getall

DELETE => /api/corporates/delete/{id}
```
