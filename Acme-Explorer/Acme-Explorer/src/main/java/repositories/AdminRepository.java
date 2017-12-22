
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Trip;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id = ?1")
	Admin findByUserAccountId(int id);

	@Query("select avg(t.tripApplications.size), min(t.tripApplications.size), max(t.tripApplications.size), sqrt(sum(t.tripApplications.size*t.tripApplications.size)/count(t.tripApplications.size)-avg(t.tripApplications.size)*avg(t.tripApplications.size)) from Trip t")
	Collection<Double> applicationsPerTripStatistics();

	@Query("select avg(m.survivalClasses.size), min(m.survivalClasses.size), max(m.survivalClasses.size), sqrt(sum(m.survivalClasses.size*m.survivalClasses.size)/count(m.survivalClasses.size)-avg(m.survivalClasses.size)*avg(m.survivalClasses.size)) from Manager m")
	Collection<Double> tripsPerManagerStatistics();

	@Query("select avg(t.price), min(t.price), max(t.price), sqrt(sum(t.price*t.price)/count(t.price)-avg(t.price)*avg(t.price)) from Trip t")
	Collection<Double> tripPricesStatistics();

	@Query("select avg(r.trips.size), min(r.trips.size), max(r.trips.size), sqrt(sum(r.trips.size*r.trips.size)/count(r.trips.size)-avg(r.trips.size)*avg(r.trips.size)) from Ranger r")
	Collection<Double> tripsPerRangerStatistics();

	@Query("select (select count(ta)*1.0 from TripApplication ta where ta.status = 'PENDING') / count(total)*1.0 from TripApplication total")
	Double pendingApplicationsRatio();

	@Query("select (select count(ta)*1.0 from TripApplication ta where ta.status = 'DUE') /count(total)*1.0 from TripApplication total")
	Double dueApplicationsRatio();

	@Query("select (select count(ta)*1.0 from TripApplication ta where ta.status = 'ACCEPTED') /count(total)*1.0 from TripApplication total")
	Double acceptedApplicationsRatio();

	@Query("select (select count(ta)*1.0 from TripApplication ta where ta.status = 'CANCELLED') / count(total)*1.0 from TripApplication total")
	Double cancelledApplicationsRatio();

	@Query("select (select count(t)*1.0 from Trip t where t.cancelationReason <> null) / count(t2)*1.0 from Trip t2 where t2.cancelationReason = null")
	Double cancelledVsOrganisedTripsRatio();

	@Query("select t from Trip t where t.tripApplications.size >= (select avg(t2.tripApplications.size)*1.1 from Trip t2) order by t.tripApplications.size")
	Collection<Trip> tripsMoreApplicationsOrdered();

	@Query("select l.trips.size from LegalText l where l.id = ?1")
	int numberOfReferencesByLegalTextId(int legalTextId);

	@Query("select min(t.notes.size), max(t.notes.size), avg(t.notes.size), sqrt(sum(t.notes.size*t.notes.size)/count(t.notes.size)-avg(t.notes.size)*avg(t.notes.size)) from Trip t")
	Collection<Double> notesPerTripStatistics();

	@Query("select min(t.audits.size), max(t.audits.size), avg(t.audits.size), sqrt(sum(t.audits.size*t.audits.size)/count(t.audits.size) -avg(t.audits.size)*avg(t.audits.size)) from Trip t")
	Collection<Double> auditsPerTripStatistics();

	@Query("select (select count(t)*1.0 from Trip t where t.audits.size <> 0) / count(t2)*1.0 from Trip t2")
	Double tripsWithAuditsRatio();

	@Query("select (select count(c)*1.0 from Curriculum c) / count(r)*1.0 from Ranger r")
	Double rangersWithCurriculumRatio();

	@Query("select (select count(c)*1.0 from Curriculum c where c.endorserRecords.size > 0) / count(r)*1.0 from Ranger r")
	Double rangersWithEndorserRecordRatio();

	@Query("select (select count(m)*1.0 from Manager m where m.isSuspicious = 1) / count(m2)*1.0 from Manager m2")
	Double suspiciousManagersRatio();

	@Query("select (select count(r)*1.0 from Ranger r where r.isSuspicious = 1) / count(r2)*1.0 from Ranger r2")
	Double suspiciousRangersRatio();
}
