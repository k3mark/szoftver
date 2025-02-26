package nationalize;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class Nationality {

    private long count;
    private String name;

    @SerializedName("country")
    private List<Country> countries;

    public Nationality(long count, String name, List<Country> countries) {
        this.count = count;
        this.name = name;
        this.countries = countries;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public static class Country {

        private String countryId;
        private float probability;

        public Country(String countryId, float probability) {
            this.countryId = countryId;
            this.probability = probability;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public float getProbability() {
            return probability;
        }

        public void setProbability(float probability) {
            this.probability = probability;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        }

    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

}
