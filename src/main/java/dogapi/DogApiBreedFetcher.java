package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://dog.ceo/api/breed";
    private static final String STATUS_CODE = "status";
    private static final String MESSAGE = "message";
    private static final String SUCCESS_CODE = "success";

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.
        client.newBuilder().build();
        final Request req = new Request.Builder()
                .url(String.format("%s/%s/list", API_URL, breed))
                .build();

        List<String> subBreedsList = new ArrayList<>();
        try {
            final Response res = client.newCall(req).execute();
            final JSONObject resBody = new JSONObject(res.body().string());
            if (resBody.getString(STATUS_CODE).equals(SUCCESS_CODE)) {
                final JSONArray subBreeds = resBody.getJSONArray(MESSAGE);
                for (int i = 0; i < subBreeds.length(); i++) {
                    subBreedsList.add((String) subBreeds.get(i));
                }
            }
            else {
                throw new BreedNotFoundException(breed);
            }
        } catch (IOException event) {
            throw new RuntimeException(event);
        }
        return subBreedsList;
    }
}