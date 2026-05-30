package com.cimmino.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimmino.shop.database.Arrivi;
import com.cimmino.shop.database.ArriviRepository;
import com.cimmino.shop.database.BinsArrivi;
import com.cimmino.shop.database.BinsArriviRepository;
import com.cimmino.shop.database.BinsVendite;
import com.cimmino.shop.database.Vendita;
import com.cimmino.shop.database.VenditeRepository;

@Service
public class BinArriviService {
	@Autowired
	private VenditeRepository venditeRepository;
	@Autowired
	BinsArriviRepository binsArriviRepository;
	@Autowired
	private ArriviRepository arriviRepository;

	public int calcAvail(Long binid, Long arrivoId) {

		// List<BinsArrivi> optbin = binsArriviRepository.findByArrivo_Id(arrivoId);
		int occ = calcOccupatiDaVendite(arrivoId, binid);
		int totBins = calcPrevistiInArrivo(arrivoId, binid);


		int avail = totBins - occ;
		return avail;
	}


	private int calcPrevistiInArrivo(Long arrivoId, Long binid) {
		Optional<Arrivi> op = arriviRepository.findById(arrivoId);
		if (op.isEmpty())
			return 0;
		Arrivi arr = op.get();
		int tot = 0;
		for (BinsArrivi a : arr.getBins()) {
			if (a.getBin().getId() == binid)
				tot += a.getNumBins();
		}
		return tot;
	}

	private int calcOccupatiDaVendite(Long arrivoId, Long binid) {
		int ret = 0;

		List<Vendita> vv = venditeRepository.findVenditeDiArrivo(arrivoId);
		for (Vendita v : vv) {
			List<BinsVendite> binsArr = v.getBins();
			for (BinsVendite bin : binsArr) {
				if (bin.getBin().getId() == binid)
					ret += bin.getNumBins();
			}
		}
		return ret;
	}

	public void delete(BinsArrivi bin) {
		binsArriviRepository.delete(bin);
		
	}
}
