package cz.kubahejda.eet.repository;

import cz.kubahejda.eet.model.Receipt;

import java.util.List;

/**
 * Created by Kuba on 30.3.2017.
 */
public interface ReceiptRepository {
    public Receipt create(Receipt entity);
    public List<Receipt> find(Long company_id);
    public Receipt findByCompanyIdAndDate(Long company_id, String date);
}
