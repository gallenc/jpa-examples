/*
Copyright (c) 2013, Bernard Butler (Waterford Institute of Technology, Ireland), Project: FAME (08/SRC/I1403)
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 -  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 -  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 -  Neither the name of WATERFORD INSTITUTE OF TECHNOLOGY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package example.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Family {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="id")
  private int familyid;

  @Column(length=30)
  private String description;

  @OneToMany(
    mappedBy="family"
   ,cascade={CascadeType.ALL}
  )
  private final List<Person> persons = new ArrayList<Person>();

  public Family() {
  }
  
  public int getFamilyid() {
    return familyid;
  }

  public void setFamilyid(int id) {
    familyid = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Person> getMembers() {
    return persons;
  }

  public void addMember(Person person) {
	if (!persons.contains(person)) {
	  persons.add(person);
	}
/* See http://stackoverflow.com/questions/2521659/foreign-key-not-stored-in-child-entity-one-to-many
   and www.java2s.com/Code/JavaAPI/javax.persistence/JoinTableinverseJoinColumns.htm */
    person.setFamily(this);
  }

  @Override
  public String toString() {
	  String memberStr = "";
	  for (Person person : persons) {
		  memberStr = memberStr + person.toString() + "\n";
	  }
	  return "Family id = " + familyid + " description = " + description + " persons = \n" + memberStr;
  }
} 
