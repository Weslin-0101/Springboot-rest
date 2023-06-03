package br.com.rest.integrationTest.vo.wrappers;

import br.com.rest.integrationTest.vo.PersonTestVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonEmbeddedVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("personVOList")
    private List<PersonTestVO> personTestVOList;

    public PersonEmbeddedVO() {
    }

    public List<PersonTestVO> getPersonTestVOList() {
        return personTestVOList;
    }

    public void setPersonTestVOList(List<PersonTestVO> personTestVOList) {
        this.personTestVOList = personTestVOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEmbeddedVO that = (PersonEmbeddedVO) o;
        return Objects.equals(personTestVOList, that.personTestVOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personTestVOList);
    }
}
