# UBITS-AWS
Proyecto de ejemplo AWS-RESTFul Web Services UBITS

Entity	HTP Action	RESTFul URL	RESTFul URL II	Bussines Method
Client	POST	/rest/clients/;<clientData>		createClient
	GET	/rest/clients/{id}	/rest/clients/	getClient
	PUT	/rest/clients/;<clientData>		editClient
	DELETE	/rest/clients/{id}		destroyClient
				
Vaccinations	POST	/rest/vaccinations/;<vaccinationData>		createVaccination
	GET	/rest/vaccinations/{id}	/rest/vaccinations/	getVaccination
	PUT	/rest/vaccinations/:<vaccinationData>		editVaccination
	DELETE	/rest/vaccinations/{id}		destroyVaccination
				
Medical Record	POST	/rest/medical-records/{idClient}/{Idvaccination}/{IdPet}	/rest/medical-records/;<medicalRecordData>	createMedicalRecord
	GET	/rest/medical-records/{idClient}/{Idvaccination}/{IdPet}	/rest/medical-records/query?client={IdClient}&vaccination={Idvaccination}&pet={IdPet}	getMedicalRecord
	PUT	/rest/medical-records/;<medicalRecordData>		editMedicalRecord
	DELETE	/rest/medical-records/{idClient}/{Idvaccination}/{IdPet}	/rest/medical-records/;<medicalRecordData>	destroyMedicalRecord
