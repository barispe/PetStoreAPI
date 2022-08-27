package petStore.petOperations;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import petStore.GlobalVars;
import petStore.areas.Pet;
import petStore.areas.Status;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.CoreMatchers.containsString;




public class Pets {
    public static String PET_ENDPOINT = GlobalVars.BASE_URL + "/pet";
    private RequestSpecification requestSpec;

    public Pets() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(GlobalVars.BASE_URL);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpec = requestSpecBuilder.build();
    }

    public Pet addNewPet(Pet pet) {
        return given(requestSpec)
                .body(pet)
                .post(PET_ENDPOINT).as(Pet.class);
    }
    public Pet addNewPetNegative(Pet pet) {
        return given(requestSpec)
                .body(responseSpecification)
                .post(PET_ENDPOINT).as(Pet.class);
    }

    public List<Pet> getPetsByStatus(Status status) {
        return given(requestSpec)
                .queryParam("status", Status.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);

    }public List<Pet> getPetsByStatusNegative(Status status) {
        return given(requestSpec)
                .queryParam("status", Status.sold.toString())
                .get(PET_ENDPOINT + "/findByCategory")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);

    }

    public void deletePet(Pet pet) {
        given(requestSpec)
                .pathParam("petId", pet.getId())
                .delete(PET_ENDPOINT + "/{petId}");
    }
    public void deletePetNegative(Pet pet) {
        given(requestSpec)
                .pathParam("petCategory", pet.getStatus())
                .delete(PET_ENDPOINT + "/{status}");
    }


    public void verifyPetDeleted(Pet pet) {
        given(requestSpec)
                .pathParam("petId", pet.getId())
                .get(PET_ENDPOINT + "/{petId}")
                .then()
                .body(containsString("Pet not found"));
    }

    public Pet findPet(Pet pet) {
        return given(requestSpec)
                .pathParam("petId", pet.getId())
                .get(PET_ENDPOINT + "/{petId}").as(Pet.class);
    }
    public Pet findPetNegative(Pet pet){
        return given(requestSpec)
                .pathParam("peTId", pet.getId())
                .get(PET_ENDPOINT + "peTId").as(Pet.class);
    }

    public Pet updatePet(Pet pet) {
        return given(requestSpec)
                .body(pet)
                .put(PET_ENDPOINT).as(Pet.class);
    }


}
