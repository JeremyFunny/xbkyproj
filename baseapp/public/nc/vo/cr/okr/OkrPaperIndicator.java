package nc.vo.cr.okr;

import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class OkrPaperIndicator {
		/**
		* ��ֱ���ϸ����
		*/
		public String pk_paper_indicator;
		
		/**
		* ��ֱ�����
		*/
		public String pk_paper;
		
		/**
		* ָ��
		*/
		public String indicator;
		
		/**
		* ָ�����
		*/
		public String indicatorclass;
		
		/**
		* ָ����ͷ�ʽ
		*/
		public int indisumrule;
		
		/**
		* ָ��Ȩ��
		*/
		public UFDouble indiweight;
		
		/**
		* ˳��
		*/
		public String indiorder;
		
		/**
		* Ŀ��ȡ����ʽ
		*/
		public String indiformula;
		
		/**
		* Ĭ���д��Ȩ��
		*/
		public Boolean isdefault;
		
		/**
		* ��ע
		*/
		public String cmemo;
		
		/**
		* ������
		*/
		public String creator;
		
		/**
		* ����ʱ��
		*/
		public UFDateTime creationtime;
		
		/**
		* �޸���
		*/
		public String mender;
		
		/**
		* �޸�ʱ��
		*/
		public UFDateTime modifytime;
		
		/**
		* ʱ���
		*/
		public UFDateTime ts;
		
		/**
		* ɾ����־
		*/
		public int dr;

		public String getPk_paper_indicator() {
			return pk_paper_indicator;
		}

		public void setPk_paper_indicator(String pk_paper_indicator) {
			this.pk_paper_indicator = pk_paper_indicator;
		}

		public String getPk_paper() {
			return pk_paper;
		}

		public void setPk_paper(String pk_paper) {
			this.pk_paper = pk_paper;
		}

		public String getIndicator() {
			return indicator;
		}

		public void setIndicator(String indicator) {
			this.indicator = indicator;
		}

		public String getIndicatorclass() {
			return indicatorclass;
		}

		public void setIndicatorclass(String indicatorclass) {
			this.indicatorclass = indicatorclass;
		}

		public int getIndisumrule() {
			return indisumrule;
		}

		public void setIndisumrule(int indisumrule) {
			this.indisumrule = indisumrule;
		}

		public UFDouble getIndiweight() {
			return indiweight;
		}

		public void setIndiweight(UFDouble indiweight) {
			this.indiweight = indiweight;
		}

		public String getIndiorder() {
			return indiorder;
		}

		public void setIndiorder(String indiorder) {
			this.indiorder = indiorder;
		}

		public String getIndiformula() {
			return indiformula;
		}

		public void setIndiformula(String indiformula) {
			this.indiformula = indiformula;
		}

		public Boolean getIsdefault() {
			return isdefault;
		}

		public void setIsdefault(Boolean isdefault) {
			this.isdefault = isdefault;
		}

		public String getCmemo() {
			return cmemo;
		}

		public void setCmemo(String cmemo) {
			this.cmemo = cmemo;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public UFDateTime getCreationtime() {
			return creationtime;
		}

		public void setCreationtime(UFDateTime creationtime) {
			this.creationtime = creationtime;
		}

		public String getMender() {
			return mender;
		}

		public void setMender(String mender) {
			this.mender = mender;
		}

		public UFDateTime getModifytime() {
			return modifytime;
		}

		public void setModifytime(UFDateTime modifytime) {
			this.modifytime = modifytime;
		}

		public UFDateTime getTs() {
			return ts;
		}

		public void setTs(UFDateTime ts) {
			this.ts = ts;
		}

		public int getDr() {
			return dr;
		}

		public void setDr(int dr) {
			this.dr = dr;
		}
		
		
}
