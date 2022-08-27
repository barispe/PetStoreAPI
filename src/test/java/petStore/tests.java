package petStore;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import petStore.areas.Category;
import petStore.areas.Pet;
import petStore.areas.Tag;
import petStore.areas.Status;
import petStore.petOperations.Pets;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;



public class tests {
    private static final String PHOTO_URL = "https://pixabay.com/photos/puppy-dog-pet-collar-dog-collar-1903313/";
    Pets Pets;
    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(10))
            .withName("My pet")
            .withPhotoUrls(Collections.singletonList(PHOTO_URL))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "golden-retriever")))
            .inCategory(new Category(1, "dogs")).build();


    @BeforeClass
    public void beforeClass() {
        Pets = new Pets();
    }

    @Test(priority = 0)
    public void addNewPet() {
        Pet petResponse = Pets.addNewPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));

    }

    @Test(priority = 1)
    public void verifyNewPet() {
        Pet petResponse = Pets.findPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 2)
    public void updatePet() {
        pet.setName("New name for my pet");
        Pet petResponse = Pets.updatePet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 3)
    public void verifyUpdatedPet() {
        Pet petResponse = Pets.findPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 4)
    public void deletePetAndDoCheck() {
        Pets.deletePet(pet);
        Pets.verifyPetDeleted(pet);
    }

    @Test(priority = 5)
    public void findPetNegativeExample(){
       Pet petResponse = Pets.findPetNegative(pet);
       assertThat(petResponse, is(samePropertyValuesAs(pet)));

    }
    @Test(priority = 6)
    public void getPetsByStatusNegativeExample(){
        Pets.getPetsByStatusNegative(Status.sold);
    }
    @Test(priority = 7)
    public void deletePetNegativeExample(){
        Pets.deletePetNegative(pet);
        Pets.verifyPetDeleted(pet);
    }
    @Test(priority = 9)
    public void addNewPetNegativeExample(){
        Pet petResponse = Pets.addNewPetNegative(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }



}

