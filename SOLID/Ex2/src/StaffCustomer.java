public class StaffCustomer extends Customer {
  private final TaxRules taxRules;
  private final DiscountRules discountRules;

  public StaffCustomer() {
    this.taxRules = new StaffTaxes();
    this.discountRules = new StaffDiscount();
  }

  public TaxRules getTaxRules() {
    return this.taxRules;
  }

  public DiscountRules getDiscountRules() {
    return this.discountRules;
  }
}
